/**
 * SampleData for Jetpack Compose Tutorial 
 */
package com.example.compose
import com.example.compose.Message


object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Emmanuel",
            "Hacer este codigo fue una totura"
        ),
        Message(
            "Emmanuel",
            """El tutorial es terrible
Los pasos a seguir se reinician a cada momento
El modo oscuro es muy enrevesado
Los import son muy raros
La documentación en github no corresponde a este proyecto
¿se supone que es para novatos, changos, tardé horrores en entenderle"
Programar aquí es para riquillos
Sería mejor un video en youtube
Oh mi Dios
Neh""".trim()
        ),
        Message(
            "Emmanuel",
            """I think Kotlin isnt my favorite programming language.
            It's so much horrible!""".trim()
        ),
        Message(
            "Emmanuel",
            "Searching for alternatives to programming here..."
        ),
        Message(
            "Emmanuel",
"""Thursday, October thirty-first ... 
The city streets are crowded for 
the holiday, even with the rain ... 
Hidden in the chaos ... is the 
element. Waiting to strike like 
snakes at the decent ... the 
vulnerable ... """.trim()
        ),
        Message(
            "Lexi",
            "It's available from API 21+ :)"
        ),
        Message(
            "Lexi",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Lexi",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Lexi",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Lexi",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Lexi",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Lexi",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
