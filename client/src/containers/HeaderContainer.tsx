import { ContainerProps } from './container'

import './container.css'

const HeaderContainer = ({ children }: ContainerProps) => {
  return <nav className="header-container h-space-between">{children}</nav>
}

export default HeaderContainer
