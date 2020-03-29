using System.Drawing;

namespace ImageFilters
{
    static class ImageConverter
    {
        public static byte[,] ImageToByteArray(Bitmap imageIn)
        {
            int x, y;
            byte[,] arr = new byte[imageIn.Width, imageIn.Height];

            for (x = 0; x < imageIn.Width; x++)
            {
                for (y = 0; y < imageIn.Height; y++)
                {
                    arr[x,y] = (byte)(255.99*imageIn.GetPixel(x, y).GetBrightness());
                }
            }

            return arr;
        }

        public static Bitmap ByteArrayToImage(byte[,] byteArrayIn)
        {
            int width = byteArrayIn.GetLength(0), height = byteArrayIn.GetLength(1);
            Bitmap im = new Bitmap(width, height);

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    im.SetPixel(x,y, Color.FromArgb(255, byteArrayIn[x, y], byteArrayIn[x, y], byteArrayIn[x, y]));
                }
            }

            return im;
        }
    }
}
