import { ContainerProps } from './container'

const ContentContainer = ({ children }: ContainerProps) => {
  return <div className="content-container vertical">{children}</div>
}

export default ContentContainer
