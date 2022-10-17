using System;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;

namespace WebApp.Controllers
{
    public class HomeController : Controller
    {
        private readonly HttpClient _client;
        private readonly IConfiguration _configuration;

        public HomeController(IConfiguration configuration)
        {
            _configuration = configuration;
            _client = new HttpClient();
        }

        public async Task<IActionResult> Index()
        {
            string url = _configuration["ApiHost"];
            ViewData["Hostname"] = url;

            try
            {
                
                ViewData["ApiResponse"] = await _client.GetStringAsync(url);
            }
            catch (Exception e)
            {
                ViewData["ApiResponse"] = e.Message;
            }

            return View();
        }
    }
}