package com.roldansworkshop.neonbutton


sealed class ButtonState {
    object Clicked : ButtonState()
    object Pressed : ButtonState()
    object Idle : ButtonState()
}