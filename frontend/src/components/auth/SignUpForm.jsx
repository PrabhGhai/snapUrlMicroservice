import { Link, useNavigate } from "react-router-dom";
import { Link2, Mail, User, Lock } from "lucide-react";
import { motion } from "framer-motion";
import { useState } from "react";
import { userSignupSchema } from "../../utils/zodSchemas";
import toast from "react-hot-toast";
import { ClipLoader } from "react-spinners";
import axiosInstance from "../../utils/axiosInstance";
const SignUpForm = () => {
    const navigate = useNavigate();
  
  const [inputs, setInputs] = useState({
    email: "",
    username: "",
    password: "",
  });

  const [errors, setErrors] = useState({
    email: "",
    username: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);

  // input handler
  const change = (e) => {
    const { name, value } = e.target;

    setInputs((prev) => ({
      ...prev,
      [name]: value,
    }));

    setErrors((prev) => ({
      ...prev,
      [name]: "",
    }));
  };

 const signupApi = () => {
  return axiosInstance.post("/api/v1/auth/signup", inputs);
};

  const handleSignup = async (e) => {
  e.preventDefault();

  // ZOD validation
  const result = userSignupSchema.safeParse(inputs);

  if (!result.success) {
    const fieldErrors = {};
    result.error.issues.forEach((issue) => {
      fieldErrors[issue.path[0]] = issue.message;
    });
    setErrors(fieldErrors);
    return;
  }

  setErrors({ email: "", username: "", password: "" });
  setLoading(true);

  toast.promise(signupApi(),
      {
        loading: "Creating your account...",
        success: (res) => {
            setInputs({
            email: "",
            username: "",
            password: "",
            })
            navigate("/login")
            return res.data.message || "Signup successful 🎉";
        },
        error: (err) => {
          return (
            err?.response?.data?.message ||
            err?.message ||
            "Signup failed ❌"
          );
        },
      }
    )
    .finally(() => {
      setLoading(false);
    });
};
  return (
     <motion.div
        className="flex items-center justify-center bg-gray-100 p-6"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
      >
        <motion.div
          className="w-full max-w-md bg-white p-8 rounded-lg shadow-md"
          initial={{ opacity: 0, y: 40 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          {/* Mobile logo */}
          <Link to="/" className="flex items-center gap-2 mb-6 lg:hidden">
            <div className="w-10 h-10 rounded-lg gradient-bg flex items-center justify-center">
              <Link2 className="w-5 h-5 text-white" />
            </div>
            <span className="text-2xl font-bold gradient-text">
              SnapUrl
            </span>
          </Link>

          <h2 className="text-2xl font-bold mb-2">Create an account</h2>
          <p className="text-sm text-gray-500 mb-6">
            Start shortening your URLs for free
          </p>

          <form className="space-y-4" onSubmit={handleSignup}>
            {/* Email */}
            <div>
              <label className="text-sm font-medium">Email</label>
              <div className="relative mt-1">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
                <input
                  type="email"
                  name="email"
                  value={inputs.email}
                  onChange={change}
                  placeholder="Enter your email"
                  className={`w-full rounded-md py-2 pl-10 pr-3 border
                    ${errors.email ? "border-red-500" : "border-gray-300"}
                    focus:outline-none focus:ring-1
                    ${errors.email ? "focus:ring-red-500" : "focus:ring-teal-800"}`}
                />
              </div>
              {errors.email && (
                <p className="text-red-500 text-xs mt-1">{errors.email}</p>
              )}
            </div>

            {/* Username */}
            <div>
              <label className="text-sm font-medium">Username</label>
              <div className="relative mt-1">
                <User className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
                <input
                  type="text"
                  name="username"
                  value={inputs.username}
                  onChange={change}
                  placeholder="Choose a username"
                  className={`w-full rounded-md py-2 pl-10 pr-3 border
                    ${errors.username ? "border-red-500" : "border-gray-300"}
                    focus:outline-none focus:ring-1
                    ${errors.username ? "focus:ring-red-500" : "focus:ring-teal-800"}`}
                />
              </div>
              {errors.username && (
                <p className="text-red-500 text-xs mt-1">
                  {errors.username}
                </p>
              )}
            </div>

            {/* Password */}
            <div>
              <label className="text-sm font-medium">Password</label>
              <div className="relative mt-1">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
                <input
                  type="password"
                  name="password"
                  value={inputs.password}
                  onChange={change}
                  placeholder="Create a password"
                  className={`w-full rounded-md py-2 pl-10 pr-3 border
                    ${errors.password ? "border-red-500" : "border-gray-300"}
                    focus:outline-none focus:ring-1
                    ${errors.password ? "focus:ring-red-500" : "focus:ring-teal-800"}`}
                />
              </div>
              {errors.password && (
                <p className="text-red-500 text-xs mt-1">
                  {errors.password}
                </p>
              )}
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              disabled={loading}
              className={`w-full py-2 rounded-md flex items-center justify-center gap-2
                text-white gradient-bg transition
                ${loading ? "opacity-70 cursor-not-allowed" : "hover:opacity-90 cursor-pointer"}`}
            >
              {loading ? <ClipLoader size={20} color="#fff" /> : "Create Account"}
            </button>
          </form>

          <p className="text-center text-sm text-gray-500 mt-4">
            Already have an account?{" "}
            <Link to="/login" className="text-teal-800 hover:underline">
              Sign in
            </Link>
          </p>
        </motion.div>
      </motion.div>
  )
}

export default SignUpForm