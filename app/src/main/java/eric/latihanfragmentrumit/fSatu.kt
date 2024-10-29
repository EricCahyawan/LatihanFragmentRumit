package eric.latihanfragmentrumit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fSatu.newInstance] factory method to
 * create an instance of this fragment.
 */
class fSatu : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var score: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_f_satu, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstNumber = arguments?.getInt("firstNumber") ?: 1
        val lastNumber = firstNumber + 4
        val numbers = Array(10) { 0 }
        var firstCardOpen = false
        var secondCardOpen = false
        var firstCardValue: Int? = null
        var secondCardValue: Int? = null

        var cardOpen = arrayOf(false, false, false, false, false, false, false, false, false, false)

        var index = 0
        for (i in firstNumber..lastNumber) {
            for (j in 1..2) {
                numbers[index] = i
                index++
            }
        }

        numbers.shuffle()

        val _score = view.findViewById<TextView>(R.id.score)
        _score.text = "Score: $score"

        val _giveUp = view.findViewById<TextView>(R.id.giveUp) // Ubah ke Button jika _giveUp adalah Button

        val textViews = arrayOf(
            view.findViewById<TextView>(R.id.tv1),
            view.findViewById<TextView>(R.id.tv2),
            view.findViewById<TextView>(R.id.tv3),
            view.findViewById<TextView>(R.id.tv4),
            view.findViewById<TextView>(R.id.tv5),
            view.findViewById<TextView>(R.id.tv6),
            view.findViewById<TextView>(R.id.tv7),
            view.findViewById<TextView>(R.id.tv8),
            view.findViewById<TextView>(R.id.tv9),
            view.findViewById<TextView>(R.id.tv10)
        )

        for (i in textViews.indices) {
            textViews[i].text = numbers[i].toString()
        }

        val cardViews = arrayOf(
            view.findViewById<CardView>(R.id.card1),
            view.findViewById<CardView>(R.id.card2),
            view.findViewById<CardView>(R.id.card3),
            view.findViewById<CardView>(R.id.card4),
            view.findViewById<CardView>(R.id.card5),
            view.findViewById<CardView>(R.id.card6),
            view.findViewById<CardView>(R.id.card7),
            view.findViewById<CardView>(R.id.card8),
            view.findViewById<CardView>(R.id.card9),
            view.findViewById<CardView>(R.id.card10)
        )

        val isCardViewsInvisible = arrayOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        )

        fun resetCards() {
            Handler(Looper.getMainLooper()).postDelayed({
                for (i in cardOpen.indices) {
                    if (cardOpen[i]) {
                        textViews[i].visibility = View.INVISIBLE
                        cardViews[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                        cardOpen[i] = false
                    }
                }
                firstCardOpen = false
                secondCardOpen = false
                firstCardValue = null
                secondCardValue = null
            }, 1000)
        }

        fun checkMatch() {
            if (firstCardValue != null && secondCardValue != null) {
                if (firstCardValue == secondCardValue) {
                    score += 10
                    _score.text = "Score: $score"

                    Handler(Looper.getMainLooper()).postDelayed({
                        for (i in cardOpen.indices) {
                            if (cardOpen[i]) {
                                textViews[i].visibility = View.INVISIBLE
                                cardViews[i].visibility = View.INVISIBLE
                                isCardViewsInvisible[i] = true // Mengubah isCardViewsInvisible menjadi true untuk kartu yang cocok
                            }
                        }

                        // Cek apakah semua kartu sudah invisible
                        if (isCardViewsInvisible.all { it }) {
                            navigateToFragmentDua()
                        }
                    }, 1000)

                } else {
                    score -= 5
                    _score.text = "Score: $score"
                    resetCards()
                }
                firstCardOpen = false
                secondCardOpen = false
                firstCardValue = null
                secondCardValue = null
            }
        }


        fun handleCardClick(cardIndex: Int, textView: TextView, card: CardView) {
            if (!firstCardOpen && !secondCardOpen) {
                textView.visibility = View.VISIBLE
                card.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                cardOpen[cardIndex] = true
                firstCardOpen = true
                firstCardValue = numbers[cardIndex]
            } else if (firstCardOpen && !secondCardOpen) {
                if (cardOpen[cardIndex]) {
                    textView.visibility = View.INVISIBLE
                    card.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
                    cardOpen[cardIndex] = false
                    firstCardOpen = false
                } else {
                    textView.visibility = View.VISIBLE
                    card.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                    cardOpen[cardIndex] = true
                    secondCardOpen = true
                    secondCardValue = numbers[cardIndex]
                    checkMatch()
                }
            }
        }

        for (i in cardViews.indices) {
            cardViews[i].setOnClickListener {
                handleCardClick(i, textViews[i], cardViews[i])
            }
        }

        // Set OnClickListener untuk tombol give up
        _giveUp.setOnClickListener {
            navigateToFragmentDua()
        }
    }

    // Method untuk pindah ke fragment fDua
    private fun navigateToFragmentDua() {
        val bundle = Bundle()
        bundle.putInt("score", score)
        val mfDua = fDua()
        mfDua.arguments = bundle
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, mfDua, fDua::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        score = 50
        view?.findViewById<TextView>(R.id.score)?.text = "Score: $score"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fSatu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

