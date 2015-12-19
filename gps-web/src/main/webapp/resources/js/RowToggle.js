CdwNoticeTables =
{
   onRowToggle : function( rowToggleDiv, noticeTableWidgetId )
   {
      // Local helper variables
      var rowToggle, prevToggle, isManualInvoked, isOpening, prevExpandedRow,

         // Grab references to previously opened rows
         previousNoticeTableWidget = CdwNoticeTables.previousNoticeTableWidget,
         previousRowToggleDiv = CdwNoticeTables.previousRowToggleDiv,
         
         // Get current PrimeFaces table widget by id
         noticeTableWidget = window[noticeTableWidgetId];
      
      // Initialize all local variables
      rowToggle = $(rowToggleDiv);
      prevToggle = $(previousRowToggleDiv);
      isManualInvoked = previousRowToggleDiv === -1;
      isOpening = rowToggle.find('.ui-icon-circle-triangle-s').length === 0;
      
      // Close previously expanded/opened rows
      if( !isManualInvoked &&
         prevToggle.length > 0 &&
         rowToggleDiv !== previousRowToggleDiv )
      {
         // Change the icon back to collapsed and find the expanded row
         prevExpandedRow = prevToggle
            .find(".ui-icon-circle-triangle-s")
            .removeClass("ui-icon-circle-triangle-s")
            .closest("tr")
            .removeClass("ui-expanded-row")
            .next("tr.ui-expanded-row-content:first");
         
         // Remove the expanded row
         prevExpandedRow.remove();
         
         // Clean up INTERNAL PrimeFaces expansion list
         previousNoticeTableWidget.expansionProcess = $.grep(
                  previousNoticeTableWidget.expansionProcess, function(g) {
                     return g != previousNoticeTableWidget
                        .getRowMeta(prevExpandedRow).index;
                  });

            // Clean up locally held previously opened row & widget
            CdwNoticeTables.previousRowToggleDiv = -1;
            CdwNoticeTables.previousNoticeTableWidget = undefined;
         }
         
         // Only set row as previous if it is opening
         if( !isManualInvoked || isOpening )
         {
            // Set current opened row as toggled
            CdwNoticeTables.previousRowToggleDiv = rowToggleDiv;
            CdwNoticeTables.previousNoticeTableWidget = noticeTableWidget;
         }
      }
   }
