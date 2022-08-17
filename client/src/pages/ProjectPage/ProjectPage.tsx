import Footer from '../../components/Footer'
import Header from '../../components/Header'
import ContentContainer from '../../containers/ContentContainer'
import PageContainer from '../../containers/PageContainer'

const ProjectPage = () => {
  return (
    <PageContainer>
      <Header theme="dark" />
      <ContentContainer>
        <div>This is project page</div>
      </ContentContainer>
      <Footer />
    </PageContainer>
  )
}

export default ProjectPage
