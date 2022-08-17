import Footer from '../../components/Footer'
import Header from '../../components/Header'
import PageContainer from '../../containers/PageContainer'
import useTheme from '../../useTheme'
import HeroSection from './components/HeroSection'
import ProjectSection from './components/ProjectSection'
import WikiSection from './components/WikiSection'

import './index.css'

const HomePage = () => {
  const theme = useTheme()
  return (
    <PageContainer>
      <Header theme="light" />
      <HeroSection />
      <ProjectSection />
      <WikiSection />
      <Footer />
    </PageContainer>
  )
}

export default HomePage
