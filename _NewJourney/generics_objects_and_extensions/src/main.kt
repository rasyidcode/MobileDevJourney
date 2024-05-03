fun main() {
//    println(Quiz.printProgressBar())
//    Quiz().printProgressBar()
//    val quiz = Quiz()
//    quiz.printQuiz()
//    val quiz = Quiz().apply { printQuiz() }
    Quiz().apply { printQuiz() }
}

//class FillInTheBlankQuestion(
//    val questionText: String,
//    val answer: String,
//    val difficulty: String
//)
//
//class TrueOrFalseQuestion(
//    val questionText: String,
//    val answer: Boolean,
//    val difficulty: String
//)
//
//class NumericQuestion(
//    val questionText: String,
//    val answer: Int,
//    val difficulty: String
//)

data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}

interface ProgressPrintable {
    val progressText: String
    fun printProgressBar()
}

class Quiz : ProgressPrintable {

    override val progressText: String
        get() = "$answered of $total answered."

    override fun printProgressBar() {
        repeat(answered) { print("▓") }
        repeat(total - answered) { print("▒") }
        println()
        println(progressText)
    }

    val question1 = Question<String>("Quoth the rave ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    fun printQuiz() {
//        println(question1.questionText)
//        println(question1.answer)
//        println(question1.difficulty)
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
//        println(question2.questionText)
//        println(question2.answer)
//        println(question2.difficulty)
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
//        println(question3.questionText)
//        println(question3.answer)
//        println(question3.difficulty)
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 5
    }
}

//val Quiz.StudentProgress.progressText: String
//    get() = "$answered of $total answered."

//fun Quiz.StudentProgress.printProgressBar() {
//    repeat(Quiz.answered) { print("▓") }
//    repeat(Quiz.total - Quiz.answered) { print("▒") }
//    println()
////    println(Quiz.progressText)
//}