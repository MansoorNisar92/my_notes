package com.mansoor.mynotes.feature_note.data.repository

import com.mansoor.mynotes.feature_note.data.data_source.NoteDao
import com.mansoor.mynotes.feature_note.domain.model.Note
import com.mansoor.mynotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(newNote: Note) {
        dao.insertNote(newNote = newNote)
    }

    override suspend fun deleteNote(deleteNote: Note) {
        dao.deleteNote(deleteNote = deleteNote)
    }
}