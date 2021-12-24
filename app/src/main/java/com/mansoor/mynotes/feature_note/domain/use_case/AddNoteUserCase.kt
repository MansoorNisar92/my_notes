package com.mansoor.mynotes.feature_note.domain.use_case

import com.mansoor.mynotes.feature_note.domain.model.InvalidNoteException
import com.mansoor.mynotes.feature_note.domain.model.Note
import com.mansoor.mynotes.feature_note.domain.repository.NoteRepository

class AddNoteUserCase(private val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(newNote: Note) {
        if (newNote.title.isBlank()){
            throw InvalidNoteException("The title of note can't be empty")
        }

        if (newNote.content.isBlank()){
            throw InvalidNoteException("The content of note can't be empty")
        }
        noteRepository.insertNote(newNote = newNote)
    }
}