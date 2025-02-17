﻿using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Windows.Forms;
using System.ComponentModel;
using System.Data;
using System.Text;
using MoreLinq;
using Accord.Imaging.Filters;

namespace ImageFilters
{
    public partial class Form1 : Form
    {
        byte[,] SourceImage { get; set; }
               
        Func<int, byte> FixRange = x => (byte)(x < 0 ? 0 : (x > 255 ? 255 : x));
        private static Image _sampleImage = null;
        static public float alpha = 0.08f;
        private static int _maxPixelValue = 256;
        private static int _imageSize = 0;
        private static GrayPixel[] _originalPixels = null;
        

        public Form1()
        {
            InitializeComponent();
            openSourceDialog.Title = "Open Image";
            openSourceDialog.InitialDirectory = Directory.GetCurrentDirectory();
        }

        private void transformButton_Click(object sender, EventArgs e)
        {
            Button btnSender = (Button)sender;
            Point ptLowerLeft = new Point(0, btnSender.Height);
            ptLowerLeft = btnSender.PointToScreen(ptLowerLeft);
            methodMenuStrip.Show(ptLowerLeft);
        }

        private void chooseButton_Click(object sender, EventArgs e)
        {
            if (openSourceDialog.ShowDialog() == DialogResult.OK)
            {
                var image = new Bitmap(openSourceDialog.FileName);
                SourceImage = ImageConverter.ImageToByteArray(image);
                sourcePictureBox.Image = image; 
                resultPictureBox.Image = null;
            }
        }

        private void ApplyTransform(Func<byte, byte> pointTransformFunc)
        {
            byte[,] result = (byte[,])SourceImage.Clone();
            Func<byte, byte> transformFunc = x => FixRange(pointTransformFunc(x));
            int width = result.GetLength(0), height = result.GetLength(1);

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    result[x, y] = transformFunc(result[x, y]);
                }
            }

            ShowTransformed(result);
        }

        private void ShowTransformed(byte[,] result)
        {
            resultPictureBox.Image = ImageConverter.ByteArrayToImage(result);
        }

        private void addConstantMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                int c = Int32.Parse(paramsTextBox.Text);
                ApplyTransform((x) => FixRange(x + c));
            } catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

           
        }

        private void multiplyByConstantMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                double c = double.Parse(paramsTextBox.Text);

                ApplyTransform((x) => FixRange((int)(c * x)));
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void toNegativeMenuItem_Click(object sender, EventArgs e)
        {
            ApplyTransform((x) => FixRange(255 - x));
        }

        private void powerTransformMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                double pow = double.Parse(paramsTextBox.Text);
                byte fMax = SourceImage.Cast<byte>().Max();

                ApplyTransform((x) => FixRange((int)(255 * Math.Pow((double)x / fMax, pow))));
            } catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void logarithmicTransformMenuItem_Click(object sender, EventArgs e)
        {
            double logMax = Math.Log(1 + SourceImage.Cast<byte>().Max());

            ApplyTransform((x) => FixRange((int)(255 * Math.Log(1 + x)/logMax)));
        }




        private void GlobalTransform(Func<byte[,], byte> thresholdFunc)
        {
            byte[,] result = (byte[,])SourceImage.Clone();
            int width = result.GetLength(0), height = result.GetLength(1);

            byte t = thresholdFunc(result);

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    result[x, y] = (byte)(result[x, y] <= t ? 0 : 255);
                }
            }

            ShowTransformed(result);
        }

        public byte GetThresholdWithHistogram(byte[,] grayScaleMatrix)
        {
            float t = 127;
            List<byte> G1 = new List<byte>();
            List<byte> G2 = new List<byte>();
            while (true)
            {
                for (int x = 0; x < grayScaleMatrix.GetLength(0); x++)
                {
                    for (int y = 0; y < grayScaleMatrix.GetLength(1); y++)
                    {
                        if (grayScaleMatrix[x, y] > t)
                        {
                            G1.Add(grayScaleMatrix[x, y]);
                        }
                        else
                        {
                            G2.Add(grayScaleMatrix[x, y]);
                        }
                    }
                }

                float m1 = GetCollectionMediumBrightness(G1);
                float m2 = GetCollectionMediumBrightness(G2);
                float newT = (m1 + m2) / 2;
                if (Math.Abs(newT - t) < 2) // TODO from params
                {
                    return (byte)newT;
                }
                t = newT;
            }
        }

        public float GetCollectionMediumBrightness(List<byte> list)
        {
            if (list.Count == 0)
            {
                return 0;
            }
            long sum = 0;
            foreach (byte brighness in list)
            {
                sum += brighness;
            }

            return (float)sum / (float)list.Count;
        }

        public byte GetThresholdWithGradient(byte[,] grayScaleMatrix)
        {
            float numerator = 0;
            float denominator = 0;
            for (int x = 0; x < grayScaleMatrix.GetLength(0) - 1; x++)
            {
                for (int y = 0; y < grayScaleMatrix.GetLength(1) - 1; y++)
                {
                    numerator += GetGradient(grayScaleMatrix, x, y) * grayScaleMatrix[x, y];
                    denominator += GetGradient(grayScaleMatrix, x, y);
                }
            }
            return (byte)(numerator / denominator);
        }

        public byte GetGradientX(byte[,] grayScaleMatrix, int x, int y)
        {
            return (byte)Math.Abs(grayScaleMatrix[x + 1, y] - grayScaleMatrix[x, y]);
        }

        public byte GetGradientY(byte[,] grayScaleMatrix, int x, int y)
        {
            return (byte)Math.Abs(grayScaleMatrix[x, y + 1] - grayScaleMatrix[x, y]);
        }

        public byte GetGradient(byte[,] grayScaleMatrix, int x, int y)
        {
            return (byte)Math.Max(GetGradientX(grayScaleMatrix, x, y), GetGradientY(grayScaleMatrix, x, y));
        }

        private void adoptiveMenuItem_Click(object sender, EventArgs e)
        {
            AdoptiveTransform();
        }

        public void AdoptiveTransform()
        {
            try
            {
                int k = 1; // TODO params
                int blockSize = 2 * k + 1;
                alpha = float.Parse(paramsTextBox.Text);

                byte[,] result = (byte[,])SourceImage.Clone();
                int width = result.GetLength(0), height = result.GetLength(1);

                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < height; y++)
                    {
                        int currentK;
                        byte t = countT(result, x, y, k, out currentK);

                        result[x, y] = (byte)(adoptiveCheckCriteria(result, x, y, k, t) ? 0 : 255);
                    }
                }

                ShowTransformed(result);
            } catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        public byte countT(byte[,] grayScaleArray, int m, int n, int k, out int newK)
        {
            int currentK = k;
            int blockSize = 2 * k + 1;
            while (true)
            {
                byte fMax = GetFMax(grayScaleArray, m, n, currentK);
                byte fMin = GetFMin(grayScaleArray, m, n, currentK);
                float p = GetP(grayScaleArray, m, n, currentK);
                float deltaFMax = Math.Abs(p - fMax);
                float deltaFMin = Math.Abs(p - fMin);
                if (deltaFMax > deltaFMin)
                {
                    newK = currentK;
                    return (byte)(alpha * (2.0f / 3.0f * fMin + 1.0f / 3.0f * p));
                }
                else if (deltaFMax < deltaFMin)
                {
                    newK = currentK;
                    return (byte)(alpha * (1.0f / 3.0f * fMin + 2.0f / 3.0f * p));
                }
                else
                {
                    if (fMax != fMin)
                    {
                        currentK++;
                        blockSize = 2 * currentK + 1;
                    }
                    else
                    {
                        newK = currentK;
                        return (byte)(alpha * p);
                    }
                }
            }
        }

        public bool adoptiveCheckCriteria(byte[,] arr, int m, int n, int k, byte t)
        {
            for (int x = -1; x <= 1; x++)
            {
                if (m + x < 0 || m + x >= arr.GetLength(0))
                {
                    continue;
                }
                for (int y = -1; y <= 1; y++)
                {
                    if (n + y < 0 || n + y >= arr.GetLength(1) || (x == 0 && y == 0))
                    {
                        continue;
                    }
                    if (Math.Abs(arr[m, n] - GetP(arr, m + x, n + y, k)) <= t)
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        static public float GetP(byte[,] arr, int m, int n, int k)
        {
            float sum = 0;
            int amount = 0;
            for (int x = -k; x <= k; x++)
            {
                if (m + x < 0 || m + x >= arr.GetLength(0))
                {
                    continue;
                }
                for (int y = -k; y <= k; y++)
                {
                    if (n + y < 0 || n + y >= arr.GetLength(1))
                    {
                        continue;
                    }
                    sum += arr[m + x, n + y];
                    amount++;
                }
            }
            return sum / amount;
        }

        static public byte GetFMax(byte[,] arr, int m, int n, int k)
        {
            byte max = 0;
            for (int x = -k; x <= k; x++)
            {
                if (m + x < 0 || m + x >= arr.GetLength(0))
                {
                    continue;
                }
                for (int y = -k; y <= k; y++)
                {
                    if (n + y < 0 || n + y >= arr.GetLength(1))
                    {
                        continue;
                    }
                    if (arr[m + x, n + y] > max)
                    {
                        max = arr[m + x, n + y];
                    }
                }
            }
            return max;
        }

        static public byte GetFMin(byte[,] arr, int m, int n, int k)
        {
            byte min = 255;
            for (int x = -k; x <= k; x++)
            {
                if (m + x < 0 || m + x >= arr.GetLength(0))
                {
                    continue;
                }
                for (int y = -k; y <= k; y++)
                {
                    if (n + y < 0 || n + y >= arr.GetLength(1))
                    {
                        continue;
                    }
                    if (arr[m + x, n + y] < min)
                    {
                        min = arr[m + x, n + y];
                    }
                }
            }
            return min;
        }


        public Image Bernsen(Image sourceImage, int windowSize, int eps)
        {
            int matrixSize = sourceImage.Height * sourceImage.Width;
            Image im = sourceImage;
            Bitmap myBitmap = new Bitmap(sourceImage.Width, sourceImage.Height);
            try
            {
                if (windowSize % 2 != 0)
                {
                    //odczyt obrazu
                    using (Bitmap bmp = new Bitmap(sourceImage))
                    {
                        Color originalColor;
                        Color framePixelColor;
                        GrayPixel[] _pixels = new GrayPixel[matrixSize];
                        GrayPixel _framePixelTemp = new GrayPixel();
                        GrayPixel[] _framePixels = new GrayPixel[(windowSize - 1) * (windowSize - 1)];

                        int j = 0;
                        int counter = 0;
                        int distance = Convert.ToInt32((windowSize - 1) * 0.5);
                        int _maxTempValue = 0;
                        int _minTempValue = 0;
                        for (int i = 0; i < matrixSize; i++)
                        {
                            _pixels[i] = new GrayPixel();
                        }

                        for (int i = 0; i < (windowSize - 1) * (windowSize - 1); i++)
                        {
                            _framePixels[i] = new GrayPixel();
                        }

                        #region
                        for (int x = 0; x < sourceImage.Width; x++)
                        {
                            for (int y = 0; y < sourceImage.Height; y++)
                            {
                                originalColor = bmp.GetPixel(x, y);
                                //create the grayscale version of the pixel
                                _pixels[j].value = (int)((originalColor.R * .3) + (originalColor.G * .59) + (originalColor.B * .11));
                                _pixels[j].R = originalColor.R;
                                _pixels[j].G = originalColor.G;
                                _pixels[j].B = originalColor.B;
                                _pixels[j].x = x;
                                _pixels[j].y = y;

                                //tworzymy liste o wielkosci okna naokolo pixela 
                                if (x >= distance && x < sourceImage.Width - distance)
                                {
                                    if (y >= distance && y < sourceImage.Height - distance)
                                    {
                                        ;
                                        //_framePixels.Clear();
                                        counter = 0;
                                        //i,k - obramowanie okna po ktorym sie poruszam naokolo danego pixela
                                        //pobieram pixele naokolo pixela i licze prog za pomoca ich wartosci
                                        for (int i = x - distance; i < x + distance; i++)
                                        {
                                            for (int k = y - distance; k < y + distance; k++)
                                            {
                                                ;
                                                //pobieram wartosci dla kazdego pixela z okna i odrazu licze prog lokalny dla obecnego pixela(centralnego z okna)
                                                framePixelColor = bmp.GetPixel(i, k);
                                                _framePixels[counter].value = (int)((framePixelColor.R * .3) + (framePixelColor.G * .59) + (framePixelColor.B * .11));
                                                _framePixels[counter].x = i;
                                                _framePixels[counter].y = k;
                                                //lista pixeli w oknie - zle dodaje do listy? WTF - do poprawy
                                                //_framePixels.Add(_framePixelTemp);
                                                counter++;
                                            }
                                        }
                                        //obliczenia na kazdym pixelu w liscie w danej chwili by obliczyc prog dla pixela na pozycji globalnej x,y

                                        _maxTempValue = _framePixels.Max(max => max.value); //max

                                        // min
                                        _minTempValue = _framePixels.Min(min => min.value);
                                        // oblicz prog dla danego globalnego pixela
                                        if (_maxTempValue - _minTempValue <= eps)
                                        {
                                            _pixels[j].threshold = 121;//Convert.ToInt32((_maxTempValue + _minTempValue) * 0.5);
                                        }
                                        else
                                        {
                                            _pixels[j].threshold = Convert.ToInt32((_maxTempValue + _minTempValue) * 0.5);
                                        }
                                    }
                                }
                                else
                                {
                                    //tu trafiaja przypadki, kiedy pixel jest za blisko brzegu obrazka - nie ruszac pixeli i wziac je z oryginalnego obrazka

                                }


                                j++;
                            }
                        }
                        #endregion
                        j = 0;
                        for (int Xcount = 0; Xcount < myBitmap.Width; Xcount++)
                        {
                            for (int Ycount = 0; Ycount < myBitmap.Height; Ycount++)
                            {
                                if (Xcount >= windowSize && Ycount >= windowSize && Xcount <= myBitmap.Width - windowSize && Ycount <= myBitmap.Height - windowSize)
                                {
                                    if (_pixels[j].value > _pixels[j].threshold)
                                    {
                                        myBitmap.SetPixel(Xcount, Ycount, Color.White);
                                    }
                                    else if (_pixels[j].value <= _pixels[j].threshold)
                                    {
                                        myBitmap.SetPixel(Xcount, Ycount, Color.Black);
                                    }
                                }
                                else
                                {
                                    myBitmap.SetPixel(Xcount, Ycount, Color.FromArgb(_pixels[j].R, _pixels[j].G, _pixels[j].B));
                                }
                                j++;

                            }
                        }

                        im = myBitmap;
                    }
                }
                else { return im; }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }

            return im;
        }
        public static Bitmap MakeGrayscale(Bitmap original)
        {
            //make an empty bitmap the same size as original
            Bitmap newBitmap = new Bitmap(original.Width, original.Height);

            for (int i = 0; i < original.Width; i++)
            {
                for (int j = 0; j < original.Height; j++)
                {
                    //get the pixel from the original image
                    Color originalColor = original.GetPixel(i, j);

                    //create the grayscale version of the pixel
                    int grayScale = (int)((originalColor.R * .3) + (originalColor.G * .59)
                        + (originalColor.B * .11));

                    //create the color object
                    Color newColor = Color.FromArgb(grayScale, grayScale, grayScale);

                    //set the new image's pixel to the grayscale version
                    newBitmap.SetPixel(i, j, newColor);
                }
            }

            return newBitmap;
        }

        public static GrayPixelHistogram[] getAllPossibleConfigurationsGrayPixels()
        {
            GrayPixelHistogram[] hst = new GrayPixelHistogram[_maxPixelValue];
            for (int i = 0; i < _maxPixelValue; i++)
                hst[i] = new GrayPixelHistogram();

            for (int i = 0; i < _maxPixelValue; i++)
            {
                hst[i].Value = i;
                hst[i].Count = 0;
            }

            return hst;
        }
        public int Otsu(GrayPixelHistogram[] histogram)
        {
            //def
            Probability[] _pixelProbability = new Probability[_maxPixelValue];
            Variance[] _thresholdVariance = new Variance[_maxPixelValue];
            Variance _threshold = new Variance();
            Bitmap myBitmap = new Bitmap(_sampleImage.Width, _sampleImage.Height);

            for (int i = 0; i < _maxPixelValue; i++)
            {
                _pixelProbability[i] = new Probability();
                _thresholdVariance[i] = new Variance();
            }

            int _counter = 0;
            double _objectProbability = 0;
            double _groundProbability = 0;
            double _objectAverage = 0;
            double _groundAverage = 0;
            //int _threshold = 0;

            //calculate probability for each pixel and store data
            foreach (var item in histogram)
            {
                _pixelProbability[_counter].pixel = item.Value;
                _pixelProbability[_counter].probability = ((double)item.Count / (double)_imageSize);
                //Console.WriteLine((float)item.Count / (float)_imageSize);
                _counter++;
            }
            _groundAverage = Int32.Parse(paramsTextBox.Text);

            //for each threshold -> t
            for (int t = 0; t < _maxPixelValue; t++)
            {
                _objectProbability = 0;
                _groundProbability = 0;
                _objectAverage = 0;
                _groundAverage = 0;

                //calculate probability of object
                for (int i = 0; i <= t; i++)
                {
                    _objectProbability += _pixelProbability[i].probability;
                }

                //calculate probability of ground
                for (int i = t + 1; i < _maxPixelValue; i++)
                {
                    _groundProbability += _pixelProbability[i].probability;
                }

                //calculate average of object
                for (int i = 0; i <= t; i++)
                {
                    _objectAverage += (i * _pixelProbability[i].probability / _objectProbability);
                }

                //calculate average of ground
                
               
                
                //calculate viariance for each threshold -> t
                _thresholdVariance[t].threshold = t;
                _thresholdVariance[t].interViariance = _objectProbability * _groundProbability * (_objectAverage - _groundAverage) * (_objectAverage - _groundAverage);
            }
            //_threshold = _thresholdVariance.GroupBy(x => x.interViariance).Select(group => group.Where(x => x.interViariance == group.Max(y => y.interViariance))).First();
            _threshold = _thresholdVariance.MaxBy(x => x.interViariance) as Variance;
            //GroupBy(x => x.Title).Select(group => group.Where(x => x.Price == group.Max(y => y.Price)).First());
            ;

            int j = 0;
            for (int Xcount = 0; Xcount < myBitmap.Width; Xcount++)
            {
                for (int Ycount = 0; Ycount < myBitmap.Height; Ycount++)
                {

                    if (_originalPixels[j].value > _threshold.threshold)
                    {
                        myBitmap.SetPixel(Xcount, Ycount, Color.White);
                    }
                    else if (_originalPixels[j].value < _threshold.threshold)
                    {
                        myBitmap.SetPixel(Xcount, Ycount, Color.Black);
                    }

                    j++;

                }
            }
            resultPictureBox.Image = myBitmap;
            return _threshold.threshold;
        }

        private void BernsenTransform_Click(object sender, EventArgs e)
        {
            try
            {
                var ss = Int32.Parse(paramsTextBox.Text);
                // var ds = sourcePictureBox.Image.Height + sourcePictureBox.Image.Width;
                resultPictureBox.Image = Bernsen(sourcePictureBox.Image, 3, ss);
            }catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

       /* private void Otsu_Click(object sender, EventArgs e)
       {
            var histogram = getGrayHistogramFromImage(_sampleImage);
            
        }*/
       
        public static GrayPixelHistogram[] getGrayHistogramFromImage(Image image)
        {
            int matrixSize = image.Height * image.Width;
            int j = 0;
            using (Bitmap bmp = new Bitmap(image))
            {
                Color originalColor;
                GrayPixelHistogram[] pixels = new GrayPixelHistogram[matrixSize];
                GrayPixelHistogram[] allHstValues = new GrayPixelHistogram[_maxPixelValue];
                GrayPixelHistogram[] sortedPixels = new GrayPixelHistogram[_maxPixelValue];

                for (int i = 0; i < matrixSize; i++)
                {
                    pixels[i] = new GrayPixelHistogram();
                    _originalPixels[i] = new GrayPixel();
                }

                for (int i = 0; i < _maxPixelValue; i++)
                {
                    allHstValues[i] = new GrayPixelHistogram();
                    sortedPixels[i] = new GrayPixelHistogram();
                }
                for (int x = 0; x < image.Width; x++)
                {
                    for (int y = 0; y < image.Height; y++)
                    {
                        originalColor = bmp.GetPixel(x, y);
                        //create the grayscale version of the pixel
                        pixels[j].Value = (int)((originalColor.R * .3) + (originalColor.G * .59) + (originalColor.B * .11));
                        _originalPixels[j].value = pixels[j].Value;
                        _originalPixels[j].R = originalColor.R;
                        _originalPixels[j].G = originalColor.G;
                        _originalPixels[j].B = originalColor.B;
                        _originalPixels[j].x = x;
                        _originalPixels[j].y = y;

                        j++;
                    }
                }

                var listOfUniqueVectors = pixels.GroupBy(l => l.Value).Select(g => new
                {
                    Value = g.Key,
                    Count = g.Select(l => l.Value).Count()
                });

                var sortedList = listOfUniqueVectors.OrderBy(r => r.Value);
                allHstValues = getAllPossibleConfigurationsGrayPixels();

                //int _counter = 0;
                //foreach (var item in sortedList)
                //{
                //    sortedPixels[_counter].Value = item.Value;
                //    sortedPixels[_counter].Count = item.Count;
                //    _counter++;
                //}

                //rewrite vectors into empty matrix
                for (int i = 0; i < _maxPixelValue; i++)
                {
                    foreach (var item in sortedList)
                    {
                        if (item.Value == allHstValues[i].Value)
                        {
                            allHstValues[i].Value = Convert.ToInt32(item.Value);
                            allHstValues[i].Count = item.Count;
                        }
                    }
                }
                return allHstValues;
            }
        }
        
        private void OtsuTransform_Click(object sender, EventArgs e)
        {
           // Bitmap input =;




            
            _sampleImage = Image.FromFile(openSourceDialog.FileName);
            _imageSize = _sampleImage.Height * _sampleImage.Width;
            _originalPixels = new GrayPixel[_imageSize];
           
            var histogram = getGrayHistogramFromImage(_sampleImage);
           int s =  Otsu(histogram);
           
        }

        private void NiblackTransforms_Click(object sender, EventArgs e)
        {
            Bitmap img = new Bitmap(openSourceDialog.FileName);
            NiblackThreshold niblack = new NiblackThreshold();
            //niblack.Radius = Int32.Parse(paramsTextBox.Text);
            niblack.K = -0.2;
            resultPictureBox.Image = niblack.Apply(img);
        }

        private void paramsTextBox_TextChanged(object sender, EventArgs e)
        {

        }
    }

    public class GrayPixelHistogram
    {
        public int Value { get; set; } //from 0 to 255
        public int Count { get; set; }
    }
    public class Variance
    {
        public int threshold { get; set; }
        public double interViariance { get; set; }
    }

    public class Probability
    {
        public int pixel { get; set; }
        public double probability { get; set; }
    }
    public class GrayPixel
    {
        public int value { get; set; }
        public int x { get; set; }
        public int y { get; set; }
        public int R { get; set; }
        public int G { get; set; }
        public int B { get; set; }
        public int threshold { get; set; } //prog lokalny dla danego pixela
    }
}
