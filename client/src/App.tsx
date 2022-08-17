import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import HomePage from './pages/HomePage/HomePage'
import ProjectPage from './pages/ProjectPage/ProjectPage'
import BlogPage from './pages/BlogPage/BlogPage'
import WikiPage from './pages/WikiPage/WikiPage'
import AboutPage from './pages/AboutPage/AboutPage'

import './App.css'
import './variables.css'

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/projects" element={<ProjectPage />} />
          <Route path="/blogs" element={<BlogPage />} />
          <Route path="/wiki" element={<WikiPage />} />
          <Route path="/about" element={<AboutPage />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
