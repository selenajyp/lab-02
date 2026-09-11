package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import java.text.StringCharacterIterator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity(it)},
                        onDeleteCity = {cityRepository.deleteCity(it)},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
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
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository{
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing",
        "Osaka", "New Delhi"
    )

    val cities: List<String> get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    fun deleteCity(city: String) {
        _cities.remove(city)
    }


}

@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf(value="")}
    var clickedCityName by remember {mutableStateOf(value="")}

    Column(modifier = modifier.fillMaxSize()){//the structure of it
        Row(modifier = Modifier.padding(16.dp)) {//the top row
            OutlinedTextField( //input box
                value = newCityName,
                onValueChange = {newCityName = it},
                label = {Text("City name" )},
                modifier = Modifier.weight(1f)
            )

            //spacing/margin?
            Spacer(modifier=Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()){
                        onAddCity(newCityName)
                        newCityName = "" //emptying the input box
                    }
                }
            ){
                Text("Add City")
            }


        }
        //delete button
        Button(
            onClick = {
                if (clickedCityName.isNotBlank()){
                    onDeleteCity(clickedCityName)
                    clickedCityName = ""
                }
            }
        ){
            Text("Delete City")
        }

        LazyColumn(modifier = modifier.fillMaxSize()) { //displays all the cities
            items(cities){city ->
                CityRow(city=city, onItemClick = {
                    clickedCityName = city
                })
            }
        }


    }
}

@Composable
fun CityRow(city: String, onItemClick: (city: String) -> Unit){
    Text(
        text=city,
        fontSize = 28.sp,
        modifier = Modifier.fillMaxWidth().clickable{onItemClick(city)}.padding(horizontal=18.dp, vertical=14.dp)
    )
}
