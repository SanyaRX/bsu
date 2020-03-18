from PIL import Image
from sys import argv as arg
from os import listdir
from os.path import isfile, join
from tabulate import tabulate

mode_to_bpp = {'1':1, 'L':8, 'P':8, 'RGB':24, 'RGBA':32, 'CMYK':32, 'YCbCr':24, 'I':32, 'F':32}
extensions = ('.jpg', '.gif', '.tif', '.bmp', '.png', '.pcx')
parameters = ['name', 'size(px)', 'dpi', 'bpp', 'compression']


def read_images(directory):
    image_names = [f for f in listdir(directory) if (isfile(join(directory, f)))]
    return zip([Image.open(join(directory, f)) for f in image_names], image_names)


def get_images_info(images):
    data = []
    for image, name in images:
        image_data = []
        image_data.append(name)
        image_data.append(image.size)
        image_data.append(image.info.get('dpi', None))
        image_data.append(mode_to_bpp[image.mode])
        image_data.append(image.info.get('compression', None))
        data.append(image_data)

    return data


def print_data(data):
    print(tabulate(data, headers=parameters))


def main(argv):
    print_data(get_images_info(read_images(argv[0])))


if __name__ == '__main__':
    if len(arg) == 1:
        print('No parameters passed. Use: image_info.exe <path_to_image_dir>')
    else:
        main(arg[1:])