import UseDashboardHeader from "../components/dashboard/UserDashboardHeader";
import CreateUrlInput from "../components/dashboard/CreateUrlInput";
import ExistingUrls from "../components/dashboard/ExistingUrls";

export default function Dashboard() {
  return (
      <div className="gradient-hero  min-h-screen" >
        <UseDashboardHeader />
        <CreateUrlInput />
        <ExistingUrls />          
      </div>
  );
}
