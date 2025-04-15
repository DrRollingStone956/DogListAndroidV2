package com.verticalcoding.mystudentlist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.verticalcoding.mystudentlist.ui.theme.MyDogListTheme


@Composable
fun GreetingColumn(name: String, onNameChange: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top=100.dp)
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = "Hello $name!"
        )
        Button(
            enabled = validateName(name),
            onClick = {
                val name = getRandomName()
                print(name)
                onNameChange(name)
            }) {
            Text(text = "Random Name")
        }
    }
}

@Composable
fun GreetingRow(name: String, onNameChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(top=100.dp)
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = "Hello $name!"
        )
        FilledTonalButton (
            enabled = validateName(name),
            onClick = {
                val name = getRandomName()
                print(name)
                onNameChange(name)
            }) {
            Text(text = "Random Name")
        }
    }
}

@Composable
fun GreetingBox(name: String, onNameChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(400.dp)
    ){
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .align(Alignment.TopStart)
        )


        FilledTonalButton (
            enabled = validateName(name),
            modifier = Modifier
                .align(Alignment.BottomStart),
            onClick = {
                val name = getRandomName()
                print(name)
                onNameChange(name)
            }) {
            Text(text = "Random Name")
        }

        LazyColumn {
            items(names) { name ->
                Text(text = name,
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

fun validateName(name: String): Boolean {
    return name.isNotEmpty() && name.length > 3
}

val names = listOf(
    "Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava", "Elijah", "Charlotte",
    "William", "Sophia", "James", "Amelia", "Benjamin", "Isabella", "Lucas", "Mia",
    "Henry", "Evelyn", "Alexander", "Harper", "Michael", "Luna", "Daniel", "Ella",
    "Matthew", "Elizabeth", "Samuel", "Sofia", "Jackson", "Emily", "Joseph", "Avery",
    "Sebastian", "Scarlett", "David", "Grace", "Carter", "Chloe", "Owen", "Victoria",
    "Wyatt", "Riley", "John", "Aria", "Jack", "Lily", "Luke", "Aubrey", "Jayden", "Zoey",
    "Dylan", "Addison", "Grayson", "Lillian", "Levi", "Natalie", "Isaac", "Hannah",
    "Gabriel", "Brooklyn", "Julian", "Samantha", "Mateo", "Zoe", "Anthony", "Eleanor",
    "Jaxon", "Leah", "Lincoln", "Audrey", "Joshua", "Skylar", "Christopher", "Ellie",
    "Andrew", "Paisley", "Theodore", "Violet", "Caleb", "Stella", "Ryan", "Aurora",
    "Asher", "Hazel", "Nathan", "Aaliyah", "Thomas", "Madelyn", "Leo", "Elena",
    "Isaiah", "Sarah", "Charles", "Ariana", "Josiah", "Penelope", "Hudson", "Lila",
    "Christian", "Layla", "Hunter", "Nora", "Connor", "Reese", "Eli", "Mackenzie",
    "Ezra", "Madeline", "Aaron", "Abigail", "Nicholas", "Willow", "Cameron", "Sadie",
    "Adrian", "Quinn", "Jonathan", "Caroline", "Nolan", "Allison", "Jeremiah", "Genesis",
    "Easton", "Eva", "Elias", "Piper", "Colton", "Gianna", "Carson", "Serenity",
    "Robert", "Autumn", "Angel", "Nevaeh", "Brayden", "Jocelyn", "Jordan", "Faith",
    "Nicholas", "Bella", "Dominic", "Katherine", "Austin", "Alexandra", "Ian", "Kylie",
    "Adam", "Brianna", "Elias", "Anna", "Jaxson", "Mary", "Greyson", "Ashley",
    "Jose", "Isabelle"
)

fun getRandomName(): String {
    return names.random()
}



@Preview(showBackground = true)
@Composable
fun GreetingColumnPreview() {
    MyDogListTheme {
        GreetingColumn("Android", onNameChange = { name ->
            print(name)
        })
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingRowPreview() {
    MyDogListTheme {
        GreetingRow("Android", onNameChange = { name ->
            print(name)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingBoxPreview() {
    MyDogListTheme {
        GreetingBox("Android", onNameChange = { name ->
            print(name)
        })
    }
}