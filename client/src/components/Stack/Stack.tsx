import { GeneralProps } from '../GeneralProps'

interface StackProps extends GeneralProps {
  direction: 'horizontal' | 'vertical'
  spacing?: string
}

const Stack = ({ direction, spacing, className, children }: StackProps) => {
  const _className =
    className === undefined ? direction : `${className} ${direction}`
  const _spacing = spacing === undefined ? '3vw' : spacing
  return (
    <div className={_className} style={{ gap: _spacing }}>
      {children}
    </div>
  )
}

export default Stack
