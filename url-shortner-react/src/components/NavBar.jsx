import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { IoIosMenu } from "react-icons/io";
import { RxCross2 } from "react-icons/rx";
import { useStoreContext } from "../contextApi/ContextApi";
import { LuLogOut } from "react-icons/lu";
import Tippy from "@tippyjs/react";
import "tippy.js/dist/tippy.css";

const Navbar = () => {
  const navigate = useNavigate();
  const { token, setToken } = useStoreContext();
  const { pathname: path } = useLocation();
  const [navbarOpen, setNavbarOpen] = useState(false);

  const onLogOutHandler = () => {
    setToken(null);
    localStorage.removeItem("JWT_TOKEN");
    navigate("/login");
  };

  // Active/inactive class definitions
  const activeClass = "text-white font-semibold border-b-2 border-white";
  const inactiveClass =
    "text-white hover:text-black font-semibold transition-all duration-150";

  return (
    <div className="h-16 bg-blue-500 z-50 flex items-center sticky top-0">
      <div className="lg:px-14 sm:px-8 px-4 w-full flex justify-between">
        <Link to="/">
          <h1 className="font-bold text-3xl text-white italic sm:mt-0 mt-2">
            UrlShortner
          </h1>
        </Link>
        <ul
          className={`flex sm:gap-10 gap-4 sm:items-center sm:mt-1 sm:pt-0 pt-3 text-slate-800 sm:static absolute left-0 top-[62px] sm:shadow-none shadow-md ${
            navbarOpen ? "h-fit sm:pb-0 pb-5" : "h-0 overflow-hidden"
          } transition-all duration-100 sm:h-fit sm:bg-none bg-blue-500 sm:w-fit w-full sm:flex-row flex-col px-4 sm:px-0`}
        >
          <li>
            <Link
              className={path === "/" ? activeClass : inactiveClass}
              to="/"
              onClick={() => setNavbarOpen(false)}
            >
              Home
            </Link>
          </li>
          <li>
            <Link
              className={
                path.startsWith("/about") ? activeClass : inactiveClass
              }
              to="/about"
              onClick={() => setNavbarOpen(false)}
            >
              About
            </Link>
          </li>
          {token && (
            <li>
              <Link
                className={
                  path.startsWith("/dashboard") ? activeClass : inactiveClass
                }
                to="/dashboard"
                onClick={() => setNavbarOpen(false)}
              >
                Dashboard
              </Link>
            </li>
          )}
          {!token && (
            <li>
              <Link
                className={path === "/login" ? activeClass : inactiveClass}
                to="/login"
                onClick={() => setNavbarOpen(false)}
              >
                Login
              </Link>
            </li>
          )}
          {/* {!token && (
            <Link to="/register" onClick={() => setNavbarOpen(false)}>
              <li className="sm:ml-0 -ml-1 bg-rose-700 text-white cursor-pointer w-24 text-center font-semibold px-2 py-2 rounded-md hover:text-slate-300 transition-all duration-150">
                SignUp
              </li>
            </Link>
          )} */}
          {token && (
            <li className="w-full sm:w-auto flex justify-center">
              <Tippy content="Sign Out" placement="bottom">
                <button
                  onClick={onLogOutHandler}
                  className="text-white cursor-pointer text-center font-semibold px-2 py-2 rounded-md"
                >
                  <LuLogOut className="w-6 h-6 transition-colors duration-200 hover:text-red-700" />
                </button>
              </Tippy>
            </li>
          )}
        </ul>
        <button
          onClick={() => setNavbarOpen(!navbarOpen)}
          className="sm:hidden flex items-center sm:mt-0 mt-2"
        >
          {navbarOpen ? (
            <RxCross2 className="text-white text-3xl" />
          ) : (
            <IoIosMenu className="text-white text-3xl" />
          )}
        </button>
      </div>
    </div>
  );
};

export default Navbar;
