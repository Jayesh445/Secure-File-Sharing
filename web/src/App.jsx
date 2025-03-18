import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import SignupFormDemo from '@/components/signup-form-demo'
import SignupPage from '@/components/SignUpPage'
import ThemeToggleButton from './components/ThemeToggleButton'

function App() {

  return (
    <>
    <ThemeToggleButton/>
    <SignupFormDemo/>
    <SignupPage/>
    </>
  )
}

export default App
