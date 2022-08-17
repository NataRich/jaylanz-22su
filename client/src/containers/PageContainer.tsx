import { ContainerProps } from './container'

const PageContainer = ({ children }: ContainerProps) => {
  return <div className="page-container v-centered">{children}</div>
}

export default PageContainer
