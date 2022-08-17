import { NavLink } from 'react-router-dom'
import FooterContainer from '../../containers/FooterContainer'
import Wrapper from '../../containers/Wrapper'
import useTheme from '../../useTheme'
import Logo from '../Logo'
import { pageLinks } from '../nav'
import Stack from '../Stack'
import Text from '../Text'

import './footer.css'

const Footer = () => {
  const theme = useTheme()

  return (
    <Wrapper className="footer-wrapper">
      <FooterContainer>
        <Stack className="v-space-between" direction="vertical">
          <Stack direction="horizontal" spacing="4vw">
            <Stack direction="vertical" spacing="4vh">
              <Logo theme="light" />
              <Stack direction="horizontal" spacing="2vw">
                {pageLinks.map(({ path, page }, idx) => {
                  return (
                    <NavLink key={idx} to={path} className="footer-navlink">
                      {page}
                    </NavLink>
                  )
                })}
              </Stack>
            </Stack>
          </Stack>

          <Text color={theme.colors.white.primary} bold>
            Copyright© 2022 Jaylanz. All Rights Reserved
          </Text>
        </Stack>
      </FooterContainer>
    </Wrapper>
  )
}

export default Footer
