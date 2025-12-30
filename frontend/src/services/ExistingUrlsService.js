import toast from "react-hot-toast";
import axiosInstance from "../utils/axiosInstance";

//create short url
export const createShortUrl = async (url)=>{
  try{
    const res = await axiosInstance.post("/api/v1/url/create-url" , {long_url:url});
    toast.success("Short url generated");
    return(res.data);
  }catch(e)
  {
    //console.log(e);
    if(e.response.status == 409)
    {
      toast.error("Short url for same already exists");
    }else{
      return e;
    }
    
  }

}

export const handleCopy = async (e,shortCode) => {
    e.preventDefault();
    const shortUrl = `http://${shortCode}`;
    await navigator.clipboard.writeText(shortUrl);
    toast.success("Short link copied to clipboard!");
  }; 

export const redirectUrl = (e,shortCode)=>
  {
    e.preventDefault();   
    window.open(`http://${shortCode}`, "_blank"); 
  }

export const handleDelete = async(e, shortUrl) => {
  e.preventDefault();
  try{
    const url = new URL(shortUrl).pathname.replace(/^\/+/, "").slice(5);
    const res = await axiosInstance.delete(`/api/v1/url/delShortUrl/${url}`);
    toast.success("Url Deleted");   
  }catch(e)
  {
   // console.log(e);
    if(e.status == 404)
    {
      toast.error("No such url exists");
    }else{
     toast.error("Internal Server Error");
    }
    
  }
};

export const fetchLinks = async () =>{

  const res = await axiosInstance.get("/api/v1/url/getProfileUrls");
  return res.data;
}