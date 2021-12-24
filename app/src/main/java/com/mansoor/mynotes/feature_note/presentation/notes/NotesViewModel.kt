package com.mansoor.mynotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mansoor.mynotes.feature_note.domain.model.Note
import com.mansoor.mynotes.feature_note.domain.use_case.NoteUseCases
import com.mansoor.mynotes.feature_note.domain.util.NoteOrder
import com.mansoor.mynotes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCase: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNoteJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                /** This will check if user has clicked already selected element and in that case
                 * below code will run*/
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                getNotes(event.noteOrder)

            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCase.deleteNoteUserCase(event.deleteNote)
                    recentlyDeletedNote = event.deleteNote
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCase.addNoteUserCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        /** We have created a note job to let compiler know that
         * previous job should be canceled because we called a
         * get notes again*/
        getNoteJob?.cancel()
        getNoteJob = notesUseCase.getNotesUseCase(noteOrder).onEach {
            _state.value = state.value.copy(notes = it, noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }
}