import { CSSProperties } from 'react'

import useTheme from '../../useTheme'
import { GeneralProps } from '../GeneralProps'

import './text.css'

interface TextProps extends GeneralProps {
  color?: string
  bold?: boolean
}

const Text = ({ color, bold, className, children }: TextProps) => {
  const theme = useTheme()
  const _style: CSSProperties = {
    color: color ? color : theme.colors.black.primary,
    fontWeight: bold ? 'bold' : 'inherit'
  }
  const _className = `text ${className ? className : ''}`
  return (
    <p className={_className} style={_style}>
      {children}
    </p>
  )
}

export default Text
