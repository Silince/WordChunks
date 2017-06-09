package com.appchamp.wordchunks.ui.levelsolved

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appchamp.wordchunks.R
import com.appchamp.wordchunks.util.Constants.CLUE_ID_KEY
import com.appchamp.wordchunks.util.Constants.COLOR_ID_KEY
import com.appchamp.wordchunks.util.Constants.FACT_ID_KEY
import com.appchamp.wordchunks.util.Constants.LEFT_ID_KEY
import kotlinx.android.synthetic.main.frag_level_solved.*
import java.util.*


class LevelSolvedFrag : Fragment() {

    private lateinit var onNextLevelListener: OnNextLevelListener

    companion object {

        fun newInstance(color: Int, clue: String, fact: String, left: Int): LevelSolvedFrag {
            val args = Bundle()
            args.putInt(COLOR_ID_KEY, color)
            args.putString(CLUE_ID_KEY, clue)
            args.putString(FACT_ID_KEY, fact)
            args.putInt(LEFT_ID_KEY, left)
            val levelSolvedFrag: LevelSolvedFrag = newInstance()
            levelSolvedFrag.arguments = args
            return levelSolvedFrag
        }

        fun newInstance() = LevelSolvedFrag()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.frag_level_solved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rlNextLevel.setOnClickListener { onNextLevelListener.onNextLevelSelected() }

        setPackColor()
        setClue()
        setFunFact()
        setLevelsLeft()
        setExcellent()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // This makes sure that the container activity has implemented
        // the onNextLevelListener interface. If not, it throws an exception
        try {
            onNextLevelListener = context as OnNextLevelListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context?.toString()
                    + " must implement OnNextLevelListener")
        }
    }

    private fun setPackColor() {
        val color = arguments.getInt(COLOR_ID_KEY)
        val drawable = imgRectBg.drawable as GradientDrawable
        drawable.setColor(color)
        tvNextLevelTitle.setTextColor(color)
    }

    private fun setClue() {
        tvNextLevelClue.text = arguments.getString(CLUE_ID_KEY)
    }

    private fun setFunFact() {
        tvFunFact.text = arguments.getString(FACT_ID_KEY)
    }

    private fun setLevelsLeft() {
        val left = arguments.getInt(LEFT_ID_KEY)
        when (left) {
            0 -> tvLevelsLeft.text = "YOU'VE FINISHED THE WHOLE PACK!"
            1 -> tvLevelsLeft.text = "ONLY ONE LEVEL LEFT IN PACK"
            -1 -> tvLevelsLeft.text = "YOU'VE FINISHED ALL PACKS AND LEVELS"
            else -> tvLevelsLeft.text = left.toString() + " LEVELS LEFT IN PACK"
        }
    }

    private fun setExcellent() {
        val res = context.resources
        val congrats = res.getStringArray(R.array.congrats)
        val i = Random().nextInt(congrats.size - 1)
        tvExcellent.text = congrats[i]
    }
}