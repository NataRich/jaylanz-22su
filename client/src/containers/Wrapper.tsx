import { GeneralProps } from '../components/GeneralProps'

const Wrapper = ({ className, children }: GeneralProps) => {
  return (
    <div className={`wrapper ${className ? className : ''}`}>{children}</div>
  )
}

export default Wrapper
