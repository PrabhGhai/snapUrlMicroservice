import AuthLayout from "../components/auth/AuthLayout";
import LoginForm from "../components/LoginForm";

const Login = () => {
  return (
   <div className="min-h-screen grid lg:grid-cols-2">
      <AuthLayout />
      <LoginForm ></LoginForm>
    </div>
  )
}

export default Login