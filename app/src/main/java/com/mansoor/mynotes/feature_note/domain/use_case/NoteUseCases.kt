package com.mansoor.mynotes.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUserCase: DeleteNoteUserCase,
    val addNoteUserCase:AddNoteUserCase,
    val getNoteUseCase: GetNoteUseCase
)
