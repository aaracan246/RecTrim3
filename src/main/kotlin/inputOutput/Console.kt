package inputOutput

import interfaces.IConsole

class Console: IConsole {
    override fun reader(msg: String) {
        readln()    // controlar y dividir más esto
    }

    override fun writer(msg: String, lineBreak: Boolean) {
        if (lineBreak) { println(msg) } else { print(msg) }
    }




}