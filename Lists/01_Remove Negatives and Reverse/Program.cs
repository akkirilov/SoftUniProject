﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _01_Remove_Negatives_and_Reverse
{
    class Program
    {
        static void Main(string[] args)
        {
            List<int> numbers = Console.ReadLine().Split().Select(int.Parse).Where(x => x >= 0).Reverse().ToList();

            if (numbers.Count == 0)
            {
                Console.WriteLine("empty");
                return;
            }
            numbers.ForEach(x => Console.Write(x + " "));    
        }
    }
}