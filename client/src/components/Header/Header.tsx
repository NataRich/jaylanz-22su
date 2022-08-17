import { NavLink } from 'react-router-dom'

import Wrapper from '../../containers/Wrapper'
import HeaderContainer from '../../containers/HeaderContainer'
import Stack from '../Stack'
import Logo from '../Logo'
import { pageLinks } from '../nav'

import './header.css'

type HeaderProps = { theme: 'dark' | 'light' }

const Header = ({ theme }: HeaderProps) => {
  return (
    <Wrapper className={`header-wrapper header-wrapper-${theme}`}>
      <HeaderContainer>
        <Logo theme={theme} />
        <Stack direction="horizontal">
          {pageLinks.map(({ path, page }, idx) => {
            const getClassName = (isActive: boolean) => {
              const cn = `header-navlink-${theme}${isActive && '-active'}`
              return `header-navlink header-navlink-${theme} ${cn}`
            }

            return (
              <NavLink
                key={idx}
                to={path}
                className={({ isActive }) => getClassName(isActive)}
              >
                {page}
              </NavLink>
            )
          })}
        </Stack>
      </HeaderContainer>
    </Wrapper>
  )
}

export default Header
