import { ReactNode } from 'react'

export interface GeneralProps {
  onClick?: () => void

  className?: string
  children?: ReactNode
}
