package com.mansoor.mynotes.feature_note.domain.repository

import com.mansoor.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(newNote: Note)

    suspend fun deleteNote(deleteNote: Note)
}