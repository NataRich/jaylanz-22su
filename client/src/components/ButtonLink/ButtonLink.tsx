import { HTMLAttributeAnchorTarget } from 'react'

import { GeneralProps } from '../GeneralProps'

import './buttonLink.css'

interface ButtonLinkProps extends GeneralProps {
  href: string
  type: 'primary' | 'default' | 'plain'
  target?: HTMLAttributeAnchorTarget
}

const ButtonLink = ({ href, type, target, children }: ButtonLinkProps) => {
  return (
    <a className={`buttonlink buttonlink-${type}`} href={href} target={target}>
      {children}
    </a>
  )
}

export default ButtonLink
