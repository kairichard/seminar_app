watch( './*\.(tex|sty)' ) { |md| 
  # use skim to reload
  system("make thesis.pdf && make clean") 
}