package com.example.convertorsytem.presentation.ui.nav_questions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.PageQuestionsBinding
import com.example.convertorsytem.presentation.ui.nav_questions.adapter.QuestionRvAdapter
import com.example.convertorsytem.presentation.ui.nav_questions.models.Questions
import com.example.convertorsytem.utils.scope

class QuestionsPage:Fragment(R.layout.page_questions) {

    private val binding by viewBinding(PageQuestionsBinding::bind)
    private lateinit var questionRvAdapter : QuestionRvAdapter
    private lateinit var questionList : ArrayList<Questions>

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) = binding.scope {
        super.onViewCreated(view , savedInstanceState)

        loadData()
        questionRvAdapter =
            QuestionRvAdapter(questionList , object:QuestionRvAdapter.OnQuestionItemListener {
                override fun onItemClick(questions : Questions) {
                    findNavController().navigate(
                        QuestionsPageDirections.actionQuestionsPageToAnswersScreen(
                            questions
                        )
                    )
                }
            })
        val dividerItemDecoration =
            DividerItemDecoration(requireContext() , DividerItemDecoration.VERTICAL)
        rvQuestions.addItemDecoration(dividerItemDecoration)
        rvQuestions.adapter = questionRvAdapter

    }

    private fun loadData() {
        questionList = ArrayList()
        for (i in 0 until 10)
            questionList.add(
                Questions(
                    id = i ,
                    questions = "Savol $i" ,
                    answer = "Javob $i" ,
                    data = "15.06.2022"
                )
            )

    }
}