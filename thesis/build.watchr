watch( './*\.(tex|sty)' ) { |md| 
  puts md.inspect
  system("make thesis.pdf && open thesis.pdf && make clean") 
}