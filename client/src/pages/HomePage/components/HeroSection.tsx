import Text from '../../../components/Text'
import ContentContainer from '../../../containers/ContentContainer'
import Wrapper from '../../../containers/Wrapper'
import useTheme from '../../../useTheme'

const HeroSection = () => {
  const theme = useTheme()
  return (
    <Wrapper className="home-hero-wrapper">
      <ContentContainer>
        <Text color={theme.colors.white.primary}>This is hero section.</Text>
      </ContentContainer>
    </Wrapper>
  )
}

export default HeroSection
