package com.example.bookstoreapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bookstoreapp.data.Book
import com.example.bookstoreapp.ui.theme.BookStoreAppTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) { uri ->

            }
            MainScreen()

        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val fs = Firebase.firestore

    val list = remember {
        mutableStateOf(emptyList<Book>())
    }

    val listener = fs.collection("books").addSnapshotListener{snapShot, exception ->
        list.value = snapShot?.toObjects(Book::class.java) ?: emptyList()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
        ) {
            items(list.value) { book ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){

                        AsyncImage(
                            //model = "https://images.all-free-download.com/images/thumbjpg/cat_hangover_relax_213869.jpg",
                            model = book.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.height(100.dp).width(100.dp)
                        )

                        Text(text = book.name, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth().padding(15.dp))
                    }


                    /*
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text(text = "Название: ${book.name}")
                        Text(text = "Описание: ${book.description}")
                        Text(text = "Цена: ${book.price} руб.")
                        Text(text = "Жанр: ${book.genre}")
                    }
                     */
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = {
                //saveBook(fs)

                /*
                val task = storage.child("cat.jpg").putBytes(
                    bitmapToByteArray(context)
                )

                task.addOnSuccessListener { uploadTask: UploadTask.TaskSnapshot ->
                    uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener{ uriTask ->
                        saveBook(fs, uriTask.result.toString())
                    }

                }

                 */
                saveBook(fs, "https://images.all-free-download.com/images/thumbjpg/cat_hangover_relax_213869.jpg")
            }) {
            Text(
                text = "Добавить книгу"
            )
        }
    }
}

private fun saveBook(fs: FirebaseFirestore){
    val bookData = hashMapOf(
        "name" to "Война и мир",
        "description" to "Роман-эпопея Льва Николаевича Толстого",
        "price" to "1500",
        "genre" to "Классическая литература",
        "timestamp" to com.google.firebase.Timestamp.now()
    )

    fs.collection("books")
        .document()
        .set(bookData)
        .addOnSuccessListener {
            println("Книга успешно добавлена")
        }
        .addOnFailureListener { e ->
            println("Ошибка при добавлении книги: $e")
        }
}

private fun bitmapToByteArrayFromInputStream(context: Context, uri: android.net.Uri): ByteArray{
    //в ресурсы добраться через контекст(Context)
    //BitMap превратить в ByteArray и его уже вернуть

    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    //val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cat)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray();
}

private fun bitmapToByteArray(context: Context): ByteArray{
    //в ресурсы добраться через контекст(Context)
    //BitMap превратить в ByteArray и его уже вернуть
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cat)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray();
}

private fun saveBook(fs: FirebaseFirestore, url: String){
    fs.collection("books")
        .document().set(
            Book(
                "My book",
                "Bla Bla",
                "100",
                "fiction",
                url
            )
        )
}