package com.mansoor.mynotes.feature_note.domain.use_case

import com.mansoor.mynotes.feature_note.domain.model.Note
import com.mansoor.mynotes.feature_note.domain.repository.NoteRepository

class DeleteNoteUserCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(deleteNote: Note) {
        noteRepository.deleteNote(deleteNote = deleteNote)
    }
}