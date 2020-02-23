# Neumorphism

Exploring the neumorphic UI element trend in Java, just for fun.
There's a few challenges to overcome in the AWT/Swing world. The first thing to do is prevent AWT
scaling your images, provide or draw images at the correct scale for the default resolution of
the monitor in use, otherwise everythign just gets blurry the wrong way.

Next is blurring deliberately, apply Gaussian smoothing. Only way it seems to do this with AWT is
using a ConvolveOp on an image, you can't blur the existing background, or at least I haven't 
found a way yet.

More to explore, more to learn.

## Getting Started

Clone to your local computer and fire up eclipse.

### Prerequisites

Being developed using Java 11

## Contributing

Very welcome, please try to follow a similar code style etc, makes it easier for anyone reading and trying to follow.

## Authors

* **Alan White** - *Initial work* - (https://github.com/alanwhite)

See also the list of [contributors](https://github.com/alanwhite/neumorphism/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details

## Acknowledgments

* The starting point [Neumorphism in user interfaces](https://uxdesign.cc/neumorphism-in-user-interfaces-b47cef3bf3a6)
* Inspiration = http://neumorphism.io
* Thank you to the authors of [Java 7 Recipes](https://books.google.co.uk/books?id=GE20llWQnUwC&printsec=frontcover) who helped me understand finally how ConvolveOp is used to blur/smooth images
* and then http://dev.theomader.com/gaussian-kernel-calculator/ for more detail

