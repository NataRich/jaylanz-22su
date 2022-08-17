import { useNavigate } from 'react-router-dom'
import LogoDark from '../../assets/logo-dark.svg'
import LogoLight from '../../assets/logo-light.svg'

import './logo.css'

type LogoProps = { theme: 'dark' | 'light' }

const Logo = ({ theme }: LogoProps) => {
  const navigate = useNavigate()
  const imgSrc = theme === 'dark' ? LogoDark : LogoLight
  return (
    <img className="logo" src={imgSrc} alt="" onClick={() => navigate('/')} />
  )
}

export default Logo
