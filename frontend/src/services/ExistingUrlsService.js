import toast from "react-hot-toast";

export const handleCopy = async (e,shortCode) => {
    e.preventDefault();
    const shortUrl = `shortify.app/${shortCode}`;
    await navigator.clipboard.writeText(shortUrl);
    toast.success("Short link copied to clipboard!");
  }; 

export const redirectUrl = (e,shortCode)=>
  {
    e.preventDefault();   
    window.open(`https://snapurl.app/${shortCode}`, "_blank"); 
  }

export const handleDelete = () => {};

export const fetchLinks = () =>{
    return [
      {
        id: "1",
        originalUrl: "https://example.com/very-long-url",
        shortCode: "abc123",
        clicks: 142,
        createdAt: "2 days ago",
      },
      {
        id: "2",
        originalUrl: "https://github.com/my-project",
        shortCode: "gh2024",
        clicks: 89,
        createdAt: "1 day ago",
      },
      {
        id: "3",
        originalUrl: "https://docs.google.com/document",
        shortCode: "doc789",
        clicks: 34,
        createdAt: "Today",
      },
    ]
}