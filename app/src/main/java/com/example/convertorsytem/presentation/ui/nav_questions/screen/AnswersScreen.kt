package com.example.convertorsytem.presentation.ui.nav_questions.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ScreenAnswersBinding
import com.example.convertorsytem.utils.scope


class AnswersScreen:Fragment(R.layout.screen_answers) {

    private val binding by viewBinding(ScreenAnswersBinding::bind)
    private val args : AnswersScreenArgs by navArgs()

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        tvQuestionAnswer.text = args.question.questions
        tvAnswer.text = args.question.answer
    }
}