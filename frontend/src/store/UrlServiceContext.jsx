import {createContext, useContext, useEffect, useState } from "react";

const UrlContext = createContext();
export const useUrlService = () => useContext(UrlContext);

export const UrlProvider = ({children}) =>{

    const [showShortUrlDiv, setShowShortUrlDiv] = useState(false);
     return (
    <UrlContext.Provider
      value={{showShortUrlDiv, setShowShortUrlDiv}}
    >
      {children}
    </UrlContext.Provider>
  );
}