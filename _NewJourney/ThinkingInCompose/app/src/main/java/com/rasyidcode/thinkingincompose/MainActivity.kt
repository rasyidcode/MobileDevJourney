package com.rasyidcode.thinkingincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rasyidcode.thinkingincompose.ui.theme.ThinkingInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThinkingInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Greeting("Android")
                        TestClickCounter()
                    }
                }
            }
        }
    }
}

/**
 * 1. Jetpack compose is Declarative UI framework
 * 2. Dynamic content, since its written in Kotlin language
 * 3. It's fast, because it's intelligently update the UI components
 *    that needs an update.
 * 4. Recomposition is happened when UI state changes and thus trigger the
 *    composable function to be redrawn again. So recomposition is a composable
 *    that is redrawn after some events triggered.
 * 5. Recomposing the entire UI tree can be computationally expensive, Compose
 *    solve this problem with this intelligent recomposition which only update
 *    the component that depends on the data.
 * 6. This to be aware of in compose
 *      - Composable functions can execute in any order, meaning it can understand
 *        which UI component will be drawn first.
 *      - Composable functions can run in parallel
 */

@Composable
fun ThinkingInComposeApp() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Greeting("Android")
            TestClickCounter()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThinkingInComposeAppPreview() {
    ThinkingInComposeTheme {
        ThinkingInComposeApp()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThinkingInComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun Greetings(names: List<String>) {
    for (name in names) {
        Text(text = "Hello $name")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() {
    ThinkingInComposeTheme {
        Greetings(names = listOf("Xavier", "Deez Nuts", "Nice", "Alul"))
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}

@Composable
fun TestClickCounter() {
    var clicksCount by remember { mutableIntStateOf(0) }

    ClickCounter(clicks = clicksCount) {
        clicksCount++
    }
}

@Preview(showBackground = true)
@Composable
fun ClickCounterPreview() {
    ClickCounter(clicks = 2) {}
}

@Composable
fun SharedPrefsToggle(
    text: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Row {
        Text(text)
        Checkbox(checked = value, onCheckedChange = onValueChanged)
    }
}

@Preview(showBackground = true)
@Composable
fun SharedPrefsTogglePreview() {
    SharedPrefsToggle(text = "Test", value = true) {

    }
}

/**
 * Compose might execute in any order
 */

/**
 * Compose functions can run in parallel
 */

/**
 * What is side-effect in Compose?
 *
 * A side-effect is any change that is visible to the rest of your app. For example,
 * these actions are all dangerous side-effects:
 * - Writing to a property of a shared object
 * - Updating an observable in ViewModel
 * - Updating shared preferences
 */

/**
 * Side-effect example
 */
@Composable
@Deprecated("Example with bug")
fun ListWithBug(myList: List<String>, modifier: Modifier = Modifier) {
    var items = 0

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item $item")
                items++ // Avoid! Side-effect of the column recomposing.
            }
        }
        Text("Count $items")
    }
}

@Preview(showBackground = true)
@Composable
fun ListWithBugPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        ListWithBug(
            myList = listOf("One", "Two", "Three", "Four"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Side-effect free example
 */
@Composable
fun ListComposable(myList: List<String>, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) {
                Text("Item: $item")
            }
        }
        Text("Count: ${myList.size}")
    }
}

@Preview(showBackground = true)
@Composable
fun ListComposablePreview() {
    ListComposable(
        myList = listOf("PHP", "Dart", "Kotlin", "C++", "Javascript"),
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Composable functions can run in parallel
 */

/**
 * Recomposition skips as much as possible, here's an example:
 */
/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        // this will recompose when [reader] changes, but not when [names] changes
        Text(header, style = MaterialTheme.typography.bodyLarge)
        Divider()

        // LazyColumn is the Compose version of a RecyclerView
        // The lambda passed to items() is similar to RecyclerView.ViewHoler
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NamePickerPreview() {
    NamePicker(header = "Names", names = listOf("Khabib", "Conor", "John", "Alex", "Israel")) {

    }
}

@Composable
fun NamePickerItem(
    name: String,
    onClicked: (String) -> Unit
) {
    Text(name, Modifier.clickable { onClicked(name) })
}

@Preview(showBackground = true)
@Composable
fun NamePickerItemPreview() {
    NamePickerItem("Boy") { println(it) }
}

/**
 * When need to perform side-effect, trigger it from a callback
 */

/**
 * Recomposition is optimistic, meaning compose expects to finish recomposition
 * before the parameters change again. If parameters does change before
 * recomposition finishes, compose might cancel recomposition and restart
 * with a new parameters.
 */

/**
 * Composable functions might run quite frequently, in some cases, a compose
 * run every frames of a UI animation. For example, if your widget tried to read
 * device settings, it could potentially read those settings hundreds of times
 * a second, with disastrous effects on your app's performance. If your composable
 * functions needs data, it should define parameters for the data, and move
 * the expensive work to another thread, outside of composition, and pass the data
 * to Compose using mutableStateOf or LiveData
 */

/**
 * Another example of side-effect, let's say we have a form that contains
 * radio button and next button, initially the next button is in disabled state,
 * which can be enable by selecting the radio button. That is a side-effect
 * example.
 */

/**
 * Construct UI by describing "what", not "how"
 */

/**
 * Unlike Views, Composable does not hold its own state that automatically change
 * due to some user event, but rather the state is controlled by value that
 * provided to it.
 */

/**
 * Summary
 *
 * - Describe what, not how
 * - UI elements are functions
 * - State controls UI
 * - Events control State
 */