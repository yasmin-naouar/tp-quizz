package com.example.quizz

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

data class Question(
    val text: String,
    val imageResId: Int,
    val choices: Array<String>,
    val correctAnswerIndex: Int,
    val imageDescription: String,
    val funFact: String
)

class MainActivity : Activity() {

    // Les cinq questions sont définies localement : aucune base de données nécessaire.
    private val questions = arrayOf(
        Question(
            "À quel pays appartient ce drapeau bleu, blanc, rouge ?",
            R.drawable.flag_france,
            arrayOf("L'Italie", "La France", "La Belgique"),
            1,
            "Drapeau à trois bandes verticales : bleu, blanc et rouge",
            "Le drapeau tricolore français date de la Révolution de 1789."
        ),
        Question(
            "Quel pays est représenté par ce cercle rouge sur fond blanc ?",
            R.drawable.flag_japon,
            arrayOf("La Chine", "La Corée du Sud", "Le Japon"),
            2,
            "Drapeau blanc avec un disque rouge au centre",
            "Le disque rouge représente le soleil : le Japon est le « pays du Soleil-Levant »."
        ),
        Question(
            "À quel pays appartient ce drapeau noir, rouge et or ?",
            R.drawable.flag_allemagne,
            arrayOf("L'Allemagne", "La Belgique", "L'Autriche"),
            0,
            "Drapeau à trois bandes horizontales : noir, rouge et jaune",
            "Attention : la Belgique utilise les mêmes couleurs, mais en bandes verticales !"
        ),
        Question(
            "Quel pays a un drapeau carré avec une croix blanche ?",
            R.drawable.flag_suisse,
            arrayOf("Le Danemark", "La Suisse", "La Suède"),
            1,
            "Drapeau carré rouge avec une croix blanche au centre",
            "Avec le Vatican, la Suisse est l'un des deux seuls pays au drapeau carré."
        ),
        Question(
            "À quel pays appartient ce drapeau vert, blanc, rouge ?",
            R.drawable.flag_italie,
            arrayOf("L'Irlande", "Le Mexique", "L'Italie"),
            2,
            "Drapeau à trois bandes verticales : vert, blanc et rouge",
            "L'Irlande a du orange à la place du rouge, et le Mexique ajoute un aigle au centre."
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var hasAnswered = false

    private lateinit var txtScore: TextView
    private lateinit var txtProgress: TextView
    private lateinit var txtQuestionNumber: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var txtFeedback: TextView
    private lateinit var txtFunFact: TextView
    private lateinit var cardQuestion: View
    private lateinit var imgQuestion: ImageView
    private lateinit var progressQuiz: ProgressBar
    private lateinit var choiceButtons: List<Button>
    private lateinit var btnNext: Button
    private lateinit var layoutResult: View
    private lateinit var txtResultEmoji: TextView
    private lateinit var txtResult: TextView
    private lateinit var txtResultMessage: TextView
    private lateinit var btnReplay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        choiceButtons.forEachIndexed { index, button ->
            button.setOnClickListener { checkAnswer(index) }
        }

        btnNext.setOnClickListener { goToNextQuestion() }
        btnReplay.setOnClickListener { restartQuiz() }

        showQuestion()
    }

    private fun bindViews() {
        txtScore = findViewById(R.id.txt_score)
        txtProgress = findViewById(R.id.txt_progress)
        txtQuestionNumber = findViewById(R.id.txt_question_number)
        txtQuestion = findViewById(R.id.txt_question)
        txtFeedback = findViewById(R.id.txt_feedback)
        txtFunFact = findViewById(R.id.txt_fun_fact)
        cardQuestion = findViewById(R.id.card_question)
        imgQuestion = findViewById(R.id.img_question)
        progressQuiz = findViewById(R.id.progress_quiz)
        choiceButtons = listOf(
            findViewById(R.id.btn_choix1),
            findViewById(R.id.btn_choix2),
            findViewById(R.id.btn_choix3)
        )
        btnNext = findViewById(R.id.btn_suivant)
        layoutResult = findViewById(R.id.layout_result)
        txtResultEmoji = findViewById(R.id.txt_result_emoji)
        txtResult = findViewById(R.id.txt_result)
        txtResultMessage = findViewById(R.id.txt_result_message)
        btnReplay = findViewById(R.id.btn_rejouer)
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        hasAnswered = false

        txtQuestionNumber.text = "Question ${currentQuestionIndex + 1} sur ${questions.size}"
        txtQuestion.text = question.text
        imgQuestion.setImageResource(question.imageResId)
        imgQuestion.contentDescription = question.imageDescription

        // Les lettres A, B, C rendent les choix plus lisibles.
        val letters = listOf("A", "B", "C")
        choiceButtons.forEachIndexed { index, button ->
            button.text = "${letters[index]}.   ${question.choices[index]}"
            button.isEnabled = true
            button.visibility = View.VISIBLE
            button.setBackgroundResource(R.drawable.bg_choice)
            button.setTextColor(getColor(R.color.ink))
        }

        txtFeedback.text = ""
        txtFunFact.text = ""
        txtFunFact.visibility = View.GONE
        btnNext.isEnabled = false
        btnNext.text = if (currentQuestionIndex == questions.lastIndex) {
            "Voir le résultat"
        } else {
            "Question suivante"
        }

        layoutResult.visibility = View.GONE
        updateScoreAndProgress()
    }

    private fun checkAnswer(selectedIndex: Int) {
        // Cette condition empêche de répondre plusieurs fois à la même question.
        if (hasAnswered) return
        hasAnswered = true

        val question = questions[currentQuestionIndex]
        val isCorrect = selectedIndex == question.correctAnswerIndex

        // La bonne réponse est toujours surlignée en vert ; un mauvais choix en rouge.
        choiceButtons[question.correctAnswerIndex].setBackgroundResource(R.drawable.bg_choice_correct)
        if (isCorrect) {
            score++
            txtFeedback.text = "✅  Bonne réponse ! Bravo."
            txtFeedback.setTextColor(getColor(R.color.success))
        } else {
            choiceButtons[selectedIndex].setBackgroundResource(R.drawable.bg_choice_wrong)
            val correctAnswer = question.choices[question.correctAnswerIndex]
            txtFeedback.text = "❌  Mauvaise réponse. C'était : $correctAnswer"
            txtFeedback.setTextColor(getColor(R.color.error))
        }

        txtFunFact.text = "💡 ${question.funFact}"
        txtFunFact.visibility = View.VISIBLE

        // La progression avance après chaque réponse, correcte ou non.
        updateScoreAndProgress()
        choiceButtons.forEach { it.isEnabled = false }
        btnNext.isEnabled = true
    }

    private fun updateScoreAndProgress() {
        txtScore.text = "Score : $score/${questions.size}"
        val answeredCount = if (hasAnswered) currentQuestionIndex + 1 else currentQuestionIndex
        progressQuiz.progress = answeredCount
        txtProgress.text = "Progression : $answeredCount/${questions.size}"
    }

    private fun goToNextQuestion() {
        // Impossible de continuer tant que la question courante n'a pas reçu de réponse.
        if (!hasAnswered) return

        if (currentQuestionIndex < questions.lastIndex) {
            currentQuestionIndex++
            showQuestion()
        } else {
            showFinalResult()
        }
    }

    private fun showFinalResult() {
        txtQuestionNumber.text = "Quiz terminé"
        cardQuestion.visibility = View.GONE
        choiceButtons.forEach { it.visibility = View.GONE }
        txtFeedback.text = ""
        txtFunFact.visibility = View.GONE
        btnNext.visibility = View.GONE

        txtResult.text = "Résultat final : $score/${questions.size}"
        when {
            score == questions.size -> {
                txtResultEmoji.text = "🏆"
                txtResultMessage.text = "Parfait ! Vous êtes un vrai expert des drapeaux."
            }
            score >= questions.size / 2 + 1 -> {
                txtResultEmoji.text = "🌍"
                txtResultMessage.text = "Bien joué ! Encore un petit effort pour le sans-faute."
            }
            else -> {
                txtResultEmoji.text = "🧭"
                txtResultMessage.text = "Pas grave, rejouez pour apprendre les drapeaux !"
            }
        }
        layoutResult.visibility = View.VISIBLE

        progressQuiz.progress = questions.size
        txtProgress.text = "Progression : ${questions.size}/${questions.size}"
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        hasAnswered = false

        cardQuestion.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        layoutResult.visibility = View.GONE

        showQuestion()
    }
}
