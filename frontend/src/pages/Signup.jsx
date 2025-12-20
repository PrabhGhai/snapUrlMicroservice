import AuthLayout from "../components/auth/AuthLayout";
import SignUpForm from "../components/auth/SignUpForm";
const Signup = () => {
  return (
    <div className="min-h-screen grid lg:grid-cols-2">

      {/* LEFT SIDE */}
      <AuthLayout />

      {/* RIGHT SIDE */}
      <SignUpForm />
     
    </div>
  );
};

export default Signup;
