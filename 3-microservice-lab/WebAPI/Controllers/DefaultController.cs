using System;
using Microsoft.AspNetCore.Mvc;

namespace WebAPI.Controllers
{
    [Route("/")]
    public class DefaultController : Controller
    {
        [HttpGet]
        public string Get()
        {
            var response = Environment.GetEnvironmentVariable("response") ?? "Hello, world!";
            return response;
        }
    }
}
