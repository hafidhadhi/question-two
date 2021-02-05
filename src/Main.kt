import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter


val JSON_DATA_STRING =
    "[{\"id\":323,\"username\":\"rinood30\",\"profile\":{\"full_name\":\"Shabrina Fauzan\",\"birthday\":\"1988-10-30\",\"phones\":[\"08133473821\",\"082539163912\"]},\"articles:\":[{\"id\":3,\"title\":\"Tips Berbagi Makanan\",\"published_at\":\"2019-01-03T16:00:00\"},{\"id\":7,\"title\":\"Cara Membakar Ikan\",\"published_at\":\"2019-01-07T14:00:00\"}]},{\"id\":201,\"username\":\"norisa\",\"profile\":{\"full_name\":\"Noor Annisa\",\"birthday\":\"1986-08-14\",\"phones\":[]},\"articles:\":[{\"id\":82,\"title\":\"Cara Membuat Kue Kering\",\"published_at\":\"2019-10-08T11:00:00\"},{\"id\":91,\"title\":\"Cara Membuat Brownies\",\"published_at\":\"2019-11-11T13:00:00\"},{\"id\":31,\"title\":\"Cara Membuat Brownies\",\"published_at\":\"2019-11-11T13:00:00\"}]},{\"id\":42,\"username\":\"karina\",\"profile\":{\"full_name\":\"Karina Triandini\",\"birthday\":\"1986-04-14\",\"phones\":[\"06133929341\"]},\"articles:\":[]},{\"id\":201,\"username\":\"icha\",\"profile\":{\"full_name\":\"Annisa Rachmawaty\",\"birthday\":\"1987-12-30\",\"phones\":[]},\"articles:\":[{\"id\":39,\"title\":\"Tips Berbelanja Bulan Tua\",\"published_at\":\"2019-04-06T07:00:00\"},{\"id\":43,\"title\":\"Cara Memilih Permainan di Steam\",\"published_at\":\"2019-06-11T05:00:00\"},{\"id\":58,\"title\":\"Cara Membuat Brownies\",\"published_at\":\"2019-09-12T04:00:00\"}]}]"

data class Person(
    val id: Int?,
    val username: String?,
    val profile : Profile,
    val articles: List<Article>
)

data class Profile(
    val fullname: String?,
    val birthday: String?,
    val phones: List<String>?
)

data class Article(
    val id: Int?,
    val title: String?,
    val publishedAt: String?
)

fun functionOne(data: List<Person>) {
    val users = data.filter { it.profile.phones.isNullOrEmpty() }.map { it.username }
    println("List of user ra duwe HP: $users")
}

fun functionTwo(data: List<Person>) {
    val users = data.filter { !it.articles.isNullOrEmpty() }.map { it.username }
    println("List of user duwe Article: $users")
}

fun functionThree(data: List<Person>) {
    val users = data.filter { it.profile.fullname?.contains("annis", true) == true }.map { it.username }
    println("User duwe Annis: $users")
}

fun functionFour(data: List<Person>) {
    fun check(list: List<Article>): Boolean {
        list.forEach {
            if (it.publishedAt?.contains("2020", true) == true) return true
        }
        return false
    }
    val users = data.filter { check(it.articles) }.map { it.username }
    println("User duwe artikel 2020: $users")
}

fun functionFive(data: List<Person>) {
    val users = data.filter { it.profile.birthday?.contains("1986") == true }.map { it.username }
    println("User lair 1986: $users")
}

fun functionSix(data: List<Person>) {
    fun check(list: List<Article>): Boolean {
        list.forEach {
            if (it.title?.contains("tips", true) == true) return true
        }
        return false
    }
    val users = data.filter { check(it.articles) }.map { it.username }
    println("User duwe artikel judul tips: $users")
}

fun functionSeven(data: List<Person>) {
    fun check(list: List<Article>): Boolean {
        list.forEach {
            it.publishedAt ?: return@forEach
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val date = LocalDate.parse(it.publishedAt, formatter)
            if (date.month.value < 8 && date.year <= 2019) return true
        }
        return false
    }
    val users = data.filter { check(it.articles) }.map { it.username }
    println("User duwe artikel before 2019 August: $users")
}

fun main(args: Array<String>) {
    val data = JSONArray(JSON_DATA_STRING).map {
        it as JSONObject
        Person(
            id = it.getInt("id"),
            username = it.getString("username"),
            profile = Profile(
                fullname = it.getJSONObject("profile").getString("full_name"),
                birthday = it.getJSONObject("profile").getString("birthday"),
                phones = it.getJSONObject("profile").getJSONArray("phones").map { phone -> (phone as String) }
            ),
            articles = it.getJSONArray("articles:").map { article ->
                article as JSONObject
                Article(
                    id = article.getInt("id"),
                    title = article.getString("title"),
                    publishedAt = article.getString("published_at")
                )
            }
        )
    }
    functionOne(data)
    functionTwo(data)
    functionThree(data)
    functionFour(data)
    functionFive(data)
    functionSix(data)
    functionSeven(data)
}
