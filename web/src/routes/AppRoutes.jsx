import Home from '@/pages/Home'
import React from 'react'
import { Router, Routes } from 'react-router-dom'

const AppRoutes = () => {
  return (
    <Router>
        <Routes>
            <Routes path="/" element={<Home/>}/>
        </Routes>        
    </Router>
  )
}

export default AppRoutes