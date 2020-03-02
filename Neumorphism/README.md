# Neumorphism

Exploring the neumorphic UI element trend, but in Java, just for fun.

![](Neumorphism/images/buttons.png)

There's a few challenges to overcome in the AWT/Swing world. 

First is blurring, i.e. applying Gaussian smoothing. Only way it seems to do this with AWT is
using a ConvolveOp on an image, you can't blur the underlying background provided by other components, 
or at least, I haven't found a way yet. 

Given we have to use images, we have to prevent AWT scaling images, we have to provide or draw images 
at the correct scale for the default resolution of the monitor in use, otherwise everything just gets blurry the wrong way.

The MultiResolutionImage approach is great for icons, assuming you can provide the various sizes needed, and we do that in 
the Example1.java app here. For images we're generating on the fly as per above, we need to avoid having to generate them 
every invocation otherwise lots of cycles spent doing that, so we cache them and invalidate the cached image if the screen 
resolution is discovered to have differed.

More to explore, more to learn.

## Coverage

JButton has NeuButtonUI: provides a raised profile, which is raised higher if mouse rolls over, and a pressed behaviour where it appears lowered
JToggleButton has NeuToggleButtonUI: provides profile in the same way as NeuButtonUI but respects selection state, persisting the lowered appearance

There is a challenge around groups of JToggleButtons, if we want them to run into each other, such that the end buttons have rounded corners but intermediate
buttons are square giving the appearance of a single run of buttons. Also do raised / lowered border work in such a UX, work to do. ANother consideration is the multiple selection, eg for Bold/Italic/Underline you should be able to select more than one, for align left/center/right it's mutually exclusive.

Would like to do a radio button next which is more like an iOS toggle. Check boxes are an unknown as they can be on menus as well as standalone. 

## Getting Started

Clone to your local computer and fire up eclipse.

Example1.java is main workhorse, building up the other components as Swing pluggable Look & Feel objects,
so one day maybe there can be whole neumorphic LaF.

Initial focus is buttons, toggle, groups and straight action ones.

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

