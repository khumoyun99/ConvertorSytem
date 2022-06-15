package com.example.convertorsytem.presentation.ui.nav_questions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convertorsytem.databinding.ItemRvQuestionsBinding
import com.example.convertorsytem.presentation.ui.nav_questions.models.Questions

class QuestionRvAdapter(
    val questionList : ArrayList<Questions> ,
    val listener : OnQuestionItemListener
):
    RecyclerView.Adapter<QuestionRvAdapter.MyQuestionVH>() {


    inner class MyQuestionVH(private val itemRvQuestionsBinding : ItemRvQuestionsBinding):
        RecyclerView.ViewHolder(itemRvQuestionsBinding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(questionList[absoluteAdapterPosition])
            }
        }

        fun onBind(questions : Questions) {
            itemRvQuestionsBinding.apply {
                tvNumber.text = (absoluteAdapterPosition + 1).toString()
                tvQuestion.text = questions.questions
            }
        }
    }


    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : MyQuestionVH {
        return MyQuestionVH(
            ItemRvQuestionsBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )
    }

    override fun onBindViewHolder(holder : MyQuestionVH , position : Int) {
        holder.onBind(questionList[position])
    }

    override fun getItemCount() : Int {
        return questionList.size
    }

    interface OnQuestionItemListener {
        fun onItemClick(questions : Questions)
    }


}